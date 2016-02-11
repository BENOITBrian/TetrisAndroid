package com.github.badoualy.badoualyve;


import com.github.badoualy.badoualyve.Listener.OnFightFinishedListener;
import com.github.badoualy.badoualyve.Listener.OnFightListener;
import com.github.badoualy.badoualyve.Listener.GetPlayerListener;
import com.github.badoualy.badoualyve.Listener.OnSignInListener;
import com.github.badoualy.badoualyve.Listener.OnSignedListener;
import com.github.badoualy.badoualyve.Models.Coord;
import com.github.badoualy.badoualyve.Models.EnumDirections;
import com.github.badoualy.badoualyve.Models.EnumTypeObject;
import com.github.badoualy.badoualyve.Models.Map;
import com.github.badoualy.badoualyve.Models.Partie;
import com.github.badoualy.badoualyve.Models.User;
import com.github.badoualy.badoualyve.Settings.GlobalSettings;
import com.github.badoualy.badoualyve.ToolsB.GlobalTools;
import com.github.badoualy.badoualyve.ui.Actors.*;
import com.github.badoualy.badoualyve.ui.Actors.FormBase;

import java.util.ArrayList;

import com.github.badoualy.badoualyve.net.NetworkOperation;

import rx.schedulers.Schedulers;

import rx.functions.Action1;

public class GameEngine implements OnSignInListener, OnFightListener, GetPlayerListener {

    private OnSignedListener playerSignedInListener;
    private OnFightFinishedListener fightFinishedListener;
    private GetPlayerListener getPlayerListener;


    // REST-API
    private static final String URL = "http://172.16.15.35:8081";
    private static final String URL_CONNECT = URL + "/connect/%s";
    private static final String URL_logout = URL + "/users/%s/logout";
    private static final String URL_addline = URL + "/users/%s/%s/addline";
    private static final String URL_deletedeteUser = URL + "/users/%s/deletedeteUser";
    private static final String URL_GET_SELF = URL + "/users/%s/%s/%s";
    private static final String URL_listuser = URL + "/users/%s/listuser";

    private User user;
    private Partie partie;
    private Map _map;
    private boolean deleteuserflag;
    private FormBase _currentBlock;
    private int _currentX;
    private int _currentY;
    private boolean _isGameOver = false;
    private ArrayList<Integer> _linesToDelete = new ArrayList<Integer>();
    private ArrayList<Integer> _linesToDeleteForAnimate = new ArrayList<Integer>();
    private ArrayList<Coord> _entplacementForRotate;
    private int _nextNb;
    private int _currentNb;
    private int _score = 0;
    private int _lines = 0;
    private boolean _first = true;
    private boolean _stepGame = false;
    private boolean isconnected = false;
    private int compt_signin = 5;
    private String nameusercurrent;
    private String numberline = "0";

    public GameEngine(OnSignedListener playerSignedInListener, OnFightFinishedListener fightFinishedListener, GetPlayerListener getPlayerListener) {
        this.playerSignedInListener = playerSignedInListener;
        this.fightFinishedListener = fightFinishedListener;
        this.getPlayerListener = getPlayerListener;
        _initMap();
    }

    public void start() {
        // Get updates from server on 500 ms interval
       /* Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        //getSelf(); // Uncomment this to get self from server instead of manual "simulation"
                        //updateUserStats();
                    }
                }).subscribe();*/
    }

    /**
     * Simulates Automatic evolution (time)
     * This method was copied from the server's code
     */
    public void updateUserStats(User user) {
        User player = Getuser();

        player.detes = user.detes;
        player.score = user.score;
        player.partieId = user.partieId;
    }


    @Override
    public void onSignIn(String name) {
        nameusercurrent = name;
        new NetworkOperation<Partie>(URL_CONNECT,Partie.class, name )
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        throwable.printStackTrace();
                        --compt_signin;
                        if(compt_signin == 0) System.exit(0);
                        System.out.println("j'arrive pas a me connecté ........");
                        onSignIn(nameusercurrent);

                    }
                })
                .doOnSuccess(new Action1<Partie>() {
                    @Override
                    public void call(Partie partieenv) {
                        if(partieenv == null){
                            --compt_signin;
                            if(compt_signin == 0) System.exit(0);
                            System.out.println("j'arrive pas a me connecté ........");
                            onSignIn(nameusercurrent);
                        }
                        GameEngine.this.isconnected = true;
                        GameEngine.this.partie = partieenv;
                        GameEngine.this.user = partieenv.user;
                        System.out.println("Connected as " + partieenv.name);
                        System.out.println("Connected as " + partieenv.user.name);
                        for (User user : partieenv.listUser) {
                            System.out.println("Connected as " + user.name);

                        }
                    }
                }).subscribe();
    }

    @Override
    public void getPlayer(final User player) {
        new NetworkOperation<Partie>(URL_GET_SELF,Partie.class, player.name,GetLines().toString() ,GetScore().toString())
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // TODO....
                        throwable.printStackTrace();
                        System.exit(0);
                    }
                })
                .doOnSuccess(new Action1<Partie>() {
                    @Override
                    public void call(Partie partie) {
                        if(partie == null){
                            getPlayer(player);
                            return;
                        }

                        System.out.println("    partie    "+ partie.name +"   gogame   "+partie.goPartie);
                        GameEngine.this.partie = partie;
                        updateuser(partie.user);

                    }
                }).subscribe();

    }

    @Override
    public void logout(User user) {

        new NetworkOperation<Partie>(URL_logout, Partie.class, user.name)
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        throwable.printStackTrace();
                        System.exit(0);

                    }
                })
                .doOnSuccess(new Action1<Partie>() {
                    @Override
                    public void call(Partie partie) {
                        System.out.println("Connected as " + partie.name);


                    }
                }).subscribe();

    }


    @Override
    public void addline(User player) {

        String detes = numberline;

        new NetworkOperation<Partie>(URL_addline,Partie.class, player.name, detes)
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // TODO....
                        throwable.printStackTrace();
                       // System.exit(0);
                    }
                })
                .doOnSuccess(new Action1<Partie>() {
                    @Override
                    public void call(Partie partie) {
                        for (User player : partie.listUser) {
                            System.out.println("add line for " + player.name);
                        }


                    }
                }).subscribe();

    }

    @Override
    public void listuser(User player) {

        new NetworkOperation<Partie>(URL_listuser, Partie.class, player.name)
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // TODO....
                        throwable.printStackTrace();
                        System.exit(0);
                    }
                })
                .doOnSuccess(new Action1<Partie>() {
                    @Override
                    public void call(Partie partie) {
                        for (User player : partie.listUser) {
                            System.out.println("add line for " + player.name);
                        }


                    }
                }).subscribe();

    }

    @Override
    public void deletedeteUser(User player) {

       new NetworkOperation<User>(URL_deletedeteUser, User.class, player.name)
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // TODO....
                        throwable.printStackTrace();
                        System.exit(0);
                    }
                })
                .doOnSuccess(new Action1<User>() {
                    @Override
                    public void call(User player) {
                        System.out.println("add line for " + player.name);
                    }
                }).subscribe();
    }


    @Override
    public void onFight() {
    }

    public Partie getPartie() {
        return this.partie;
    }

    public boolean getisconnected(){return this.isconnected;}
    public void setNumberLine(String lines){ this.numberline = lines;}

    public void setUser(Partie partie, String username) {

        for (User userloop : partie.listUser) {
            if (userloop.name == username) {
                System.out.println("setUser()   " + username);
                this.user = userloop;

            }
        }
    }

    public User getuserinpartie(Partie partie) {
        User thisuser = null;
        for (User userloop : partie.listUser) {
            if (userloop.name == this.user.name) {
                System.out.println("cooool" + this.user.name);
                thisuser = userloop;

            }
        }
        return thisuser;
    }

    public void updateuser(User username) {

        System.out.println("updateuser   " + username.name);
        this.user.name = username.name;
        this.user.detes = username.detes;
        this.user.score = username.score;
        this.user.partieId = username.partieId;


    }

    public Map GetMap() {
        return _map;
    }

    public User Getuser() {
        return this.user;
    }

    public void SetStepGame(boolean value) {
        _stepGame = value;
    }

    public boolean GetStepGame() {
        return _stepGame;
    }

    public boolean IsGameOver() {
        return _isGameOver;
    }

    public ArrayList<Integer> GetLineAnimate() {
        return _linesToDeleteForAnimate;
    }

    public void reloadLineAnimate() {
        _linesToDeleteForAnimate = new ArrayList<Integer>();
    }

    public boolean Move(EnumDirections dir, boolean isVirtual) {

        if (!_isGameOver) {
            boolean movedBlock = false;
            EnumTypeObject detect = _canPlaceBlock(dir, _currentBlock);

            switch (dir) {
                case DOWN:
                    if (detect == EnumTypeObject.EMPTY) {
                        if (!isVirtual) {
                            _currentY--;
                        }
                        movedBlock = true;
                    }
                    break;
                case LEFT:
                    if (detect == EnumTypeObject.EMPTY) {
                        if (!isVirtual) {
                            _currentX--;
                        }

                        movedBlock = true;
                    } else if (detect == EnumTypeObject.OVER) {
                        return false;
                    }
                    break;
                case RIGHT:
                    if (detect == EnumTypeObject.EMPTY) {
                        if (!isVirtual) {
                            _currentX++;
                        }

                        movedBlock = true;
                    } else if (detect == EnumTypeObject.OVER) {
                        return false;
                    }
                    break;
            }

            if (movedBlock) {
                if (!isVirtual) {
                    _clearLastCoord();
                    _placeBlock(_currentBlock, false, _currentX, _currentY);
                }
                return true;
            } else {
                if (dir == EnumDirections.DOWN) {

                    if (!isVirtual) {
                        if (GlobalSettings.USE_SERVER) {
                            if (this.user.detes != 0) {

                                AddLine((int) this.user.detes);
                                deletedeteUser(this.user);

                            }
                        }

                        _isGameOver = _displayNewBlock();
                        if (!_isGameOver) {
                            _score = _score + 10;
                            _nextNb = GlobalTools.GenerateRandomNumber(1, 7);
                        } else {
                            if (GlobalSettings.USE_SERVER) {
                                //deleteuser
                                deleteuserflag = true;
                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {
                                                logout(Getuser());
                                            }
                                        },
                                        5000
                                );
                            }
                        }
                    }

                }
            }

        }
        return false;
    }

    public void Rotate() {
        if (_placeBlockRotate()) {
            if (_canPlaceBlock(EnumDirections.ROTATE, _currentBlock) == EnumTypeObject.EMPTY) {
                _clearLastCoord();
                _currentBlock.Rotate();
                _placeBlock(_currentBlock, false, _currentX, _currentY);
            }
        }
    }

    private boolean _placeBlockRotate() {
        int realX = 0;
        int realY = 0;
        _entplacementForRotate = _currentBlock.VirtualRotate();
        if (_entplacementForRotate != null) {
            for (Coord cr : _entplacementForRotate) {
                realX = _currentX + cr.GetRelativeX();
                realY = _currentY - cr.GetRelativeY();
                cr.SetRealCoord(realX, realY);
            }
            return true;
        } else {
            return false;
        }
    }

    public FormBase GetNextBlock(int x, int y) {
        FormBase res = _createBlock(_nextNb);
        _placeBlock(res, true, x, y);
        return res;
    }

    public Integer GetScore() {
        return _score;
    }

    public Integer GetLines() {
        return _lines;
    }

    private EnumTypeObject _canPlaceBlock(EnumDirections dir, FormBase fb) {
        int deltaX = 0;
        int deltaY = 0;
        ArrayList<Coord> entplacement = fb.GetEntplacement();
        switch (dir) {
            case DOWN:
                deltaY = -1;
                break;
            case LEFT:
                deltaX = -1;
                break;
            case RIGHT:
                deltaX = 1;
                break;
            case ROTATE:
                deltaX = 0;
                entplacement = _entplacementForRotate;
                break;
        }

        for (Coord cr : entplacement) {

            EnumTypeObject type = _map.GetTypeObjectByCoord(cr.GetRealX() + deltaX, cr.GetRealY() + deltaY);
            if (type == EnumTypeObject.OVER) {
                return EnumTypeObject.OVER;
            }

            FormBase obj = _map.GetObjectByCoord(cr.GetRealX() + deltaX, cr.GetRealY() + deltaY);

            if (type != EnumTypeObject.EMPTY && obj != null && obj.GetEntplacement().get(0).GetRecognition() == cr.GetRecognition()) {
                continue;
            } else if (type != EnumTypeObject.EMPTY) {
                return EnumTypeObject.BLOCK;
            }

        }
        return EnumTypeObject.EMPTY;
    }

    public void _deleteLine() {
        reloadLineAnimate();
        for (int line = _linesToDelete.size() - 1; line >= 0; line--) {
            for (int y = _linesToDelete.get(line) + 1; y < GlobalSettings.SIZE_ARRAY_Y - 1; y++) {
                for (int x = 0; x < GlobalSettings.SIZE_ARRAY_X; x++) {
                    if (_map.GetTypeObjectByCoord(x, y) != EnumTypeObject.EMPTY) {
                        _map.SetPosition(x, y - 1, _map.GetObjectByCoord(x, y));
                        _map.SetPosition(x, y, new BlockEmpty());
                    }
                }
            }
        }

        if (_linesToDelete.size() > 3) {
            _score = _score + 50;
        } else if (_linesToDelete.size() > 2) {
            _score = _score + 35;
        } else if (_linesToDelete.size() > 1) {
            _score = _score + 15;
        } else {
            _score = _score + 8;
        }
        _lines = _lines + _linesToDelete.size();
        _linesToDelete = new ArrayList<Integer>();
    }

    public boolean _checkDeleteLine() {
        boolean isLineFull;
        for (int y = 0; y < GlobalSettings.SIZE_ARRAY_Y - 1; y++) {
            isLineFull = true;
            for (int x = 0; x < GlobalSettings.SIZE_ARRAY_X; x++) {
                if (_map.GetTypeObjectByCoord(x, y) == EnumTypeObject.EMPTY) {
                    isLineFull = false;
                    continue;
                }
            }
            if (isLineFull) {
                _linesToDelete.add(y);
            }
        }
        if (_linesToDelete.size() > 0) {
            _linesToDeleteForAnimate = _linesToDelete;
            return true;
        } else {
            return false;
        }
    }

    private void _clearLastCoord() {
        for (Coord cr : _currentBlock.GetEntplacement()) {
            _map.SetPosition(cr.GetRealX(), cr.GetRealY(), new BlockEmpty());
        }
    }

    private void _placeBlock(FormBase fb, boolean isVirtual, int cX, int cY) {
        int realX = 0;
        int realY = 0;

        for (Coord cr : fb.GetEntplacement()) {
            realX = cX + cr.GetRelativeX();
            realY = cY - cr.GetRelativeY();
            cr.SetRealCoord(realX, realY);

            if (!isVirtual) {
                _currentBlock = fb;
                _map.SetPosition(realX, realY, _currentBlock);
            }

        }
    }

    private FormBase _createBlock(int nb) {
        FormBase tmpB;

        if (nb == 1) {
            tmpB = new BlockL();
        } else if (nb == 2) {
            tmpB = new BlockSquare();
        } else if (nb == 3) {
            tmpB = new BlockZ();
        } else if (nb == 4) {
            tmpB = new BlockStick();
        } else if (nb == 5) {
            tmpB = new BlockZRev();
        } else if (nb == 6) {
            tmpB = new BlockLRev();
        } else {
            tmpB = new BlockMid();
        }
        return tmpB;
    }

    private boolean _displayNewBlock() {
        _currentX = GlobalSettings.SIZE_ARRAY_X / 2;
        _currentY = GlobalSettings.SIZE_ARRAY_Y - 1;
        if (!_first) {
            _currentNb = _nextNb;
        } else {
            _first = false;
        }

        FormBase tmpB = _createBlock(_currentNb);
        _placeBlock(tmpB, true, _currentX, _currentY);
        if (_canPlaceBlock(EnumDirections.DOWN, tmpB) == EnumTypeObject.BLOCK) {
            return true;
        }
        _placeBlock(tmpB, false, _currentX, _currentY);
        return false;
    }

    public void AddLine(int nbLine) {

        System.out.println("nbLine-----------------------------------------------------   " + nbLine);
        if (_map.FormMustTop() + nbLine >= GlobalSettings.SIZE_ARRAY_Y - 1) {
            _isGameOver = true;
            return;
        }
        for (int y = GlobalSettings.SIZE_ARRAY_Y - 1; y >= 0; y--) {
            for (int x = 0; x < GlobalSettings.SIZE_ARRAY_X; x++) {
                if (_map.GetTypeObjectByCoord(x, y) != EnumTypeObject.EMPTY) {
                    _map.SetPosition(x, y + nbLine, _map.GetObjectByCoord(x, y));
                    _map.SetPosition(x, y, new BlockEmpty());
                }
            }
        }

        for (int y = 0; y < nbLine; y++) {
            int random = GlobalTools.GenerateRandomNumber(0, GlobalSettings.SIZE_ARRAY_X);
            for (int x = 0; x < GlobalSettings.SIZE_ARRAY_X; x++) {
                _map.SetPosition(x, y, new BlockSkull());
                _map.SetPosition(random, y, new BlockEmpty());
            }
        }
    }

    public void print() {

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (!deleteuserflag) {

                            User user = Getuser();
                            if (user != null) {
                                getPlayer(user);


                            }

                        }
                    }
                },
                2000, 2000
        );
    }

    private void _initMap() {
        _map = new Map();

        for (int x = 0; x < GlobalSettings.SIZE_ARRAY_X; x++) {
            for (int y = 0; y < GlobalSettings.SIZE_ARRAY_Y; y++) {
                _map.SetPosition(x, y, new BlockEmpty());
            }
        }
        _currentNb = GlobalTools.GenerateRandomNumber(1, 7);
        _nextNb = GlobalTools.GenerateRandomNumber(1, 7);
        _displayNewBlock();
    }
}
